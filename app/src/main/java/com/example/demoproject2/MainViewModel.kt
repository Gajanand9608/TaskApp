package com.example.demoproject2

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject2.model.Response
import com.example.demoproject2.repository.OlxRepository
import com.example.demoproject2.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val olxRepository: OlxRepository) : ViewModel() {

    private var _olxDataMutableSte : MutableState<Response?> = mutableStateOf(null)
    val olxDataState : State<Response?>  = _olxDataMutableSte

    private var _showLoader : MutableLiveData<Boolean?> = MutableLiveData(null)
    val showLoader : LiveData<Boolean?> = _showLoader

    private val _showErrorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val showErrorMessage: LiveData<String?> = _showErrorMessage

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            showLoader(true)
            val response = try{
                olxRepository.getData()
            }catch (e : Exception){
                null
            }
            showLoader(false)
            if(response != null){
                val responseBody = response.body()
                if(response.isSuccessful &&  responseBody != null){
                    _olxDataMutableSte.value = responseBody
                }else {
                    _showErrorMessage.postValue("Something went wrong")
                }
            }else{
                _showErrorMessage.postValue("Something went wrong")
            }
        }
    }

    fun showLoader(show: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (show != _showLoader.value) {
                _showLoader.postValue(show)
            }
        }
    }

}