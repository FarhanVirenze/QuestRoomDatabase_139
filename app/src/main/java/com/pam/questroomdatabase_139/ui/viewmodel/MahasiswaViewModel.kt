package com.pam.questroomdatabase_139.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pam.questroomdatabase_139.Mahasiswa
import com.pam.questroomdatabase_139.repository.RepositoryMhs
import kotlinx.coroutines.launch

class MahasiswaViewModel(private val repositoryMhs: RepositoryMhs): ViewModel() {
    var uiState by mutableStateOf(MhsUIState())

    // memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent
        )
    }

    //validasi data inout pengguna
    private fun validateField(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "nim tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "nama tidak boleh kosong",
            jeniskelamin = if (event.jenisKelamin.isNotEmpty()) null else "jenis kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "angkatan tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent
        if(validateField()){
            viewModelScope.launch {
                try{
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "data gagal disimpan"
                    )
                }
            }
        }else{
            uiState=uiState.copy(
                snackBarMessage = "Input tidak valid periksa kembali data anda"
            )
        }
    }
    //reset pesan snackbar
    fun resetSnackBarMessage(){
        uiState=uiState.copy(snackBarMessage = null)
    }


}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid:FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)
data class FormErrorState(
    val nim:String? =null,
    val nama:String?= null,
    val alamat:String?=null,
    val jeniskelamin:String?=null,
    val kelas: String?=null,
    val angkatan:String?=null
){
    fun isValid():Boolean{
        return nim ==null && nama ==null &&jeniskelamin==null&&alamat==null&&kelas==null&&angkatan==null
    }
}

//menyimpan input form ke entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jeniskelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)
//data class variable yang ,menyimpan data input form
data class MahasiswaEvent(
    val nim:String ="",
    val nama:String="",
    val alamat:String="",
    val jenisKelamin:String="",
    val kelas: String="",
    val angkatan:String=""
)