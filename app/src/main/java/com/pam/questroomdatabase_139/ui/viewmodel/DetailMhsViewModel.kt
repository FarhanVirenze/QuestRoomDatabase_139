package com.pam.questroomdatabase_139.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.questroomdatabase_139.Mahasiswa
import com.pam.questroomdatabase_139.repository.RepositoryMhs
import com.pam.questroomdatabase_139.ui.navigation.DestinasiDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {

    // Mendapatkan NIM dari SavedStateHandle
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    // Mendapatkan detail mahasiswa dan mengelola UI state
    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull() // Pastikan data tidak null
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true)) // Set status loading saat mulai
            delay(600) // Simulasi delay jika diperlukan
        }
        .catch { throwable ->
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    // Fungsi untuk menghapus mahasiswa
    fun deleteMhs() {
        detailUiState.value.detailUiEvent.toMahasiswaEntity().let {
            viewModelScope.launch {
                repositoryMhs.deleteMhs(it)
            }
        }
    }
}

// Data class untuk UI State
data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MahasiswaEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MahasiswaEvent()
}

// Fungsi ekstensi untuk mengubah Mahasiswa menjadi MahasiswaEvent
fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jeniskelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}
