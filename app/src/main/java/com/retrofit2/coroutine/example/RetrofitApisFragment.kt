package com.retrofit2.coroutine.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.retrofit2.coroutine.example.databinding.FragmentApiBinding
import com.retrofit2.coroutine.example.http.reqBody.ReqLogin
import com.retrofit2.coroutine.example.reference.ApiProvider
import com.retrofit2.coroutine.example.reference.Network

class RetrofitApisFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentApiBinding.inflate(inflater, container, false)

        binding.deferredGetBtn.setOnClickListener{
            Network.request(
                ApiProvider.provideApi().getCarriersAsync(),
                success = {
                    for (item in it){
                        addLog(item.toString())
                    }
                },
                error = {
                    addLog(it.toString())
                }
            )
        }

        binding.deferredPostBtn.setOnClickListener {
            val token = "google id_token"
            val reqLogin = ReqLogin.createReqLoginInfo("GOOGLE", token)
            Network.request(
                ApiProvider.provideApi().postLogin(reqLogin),
                success = {
                    addLog(it.toString())
                },
                error = {
                    addLog(it.message.toString())
                }
            )
        }

        binding.suspendGetListBtn.setOnClickListener{
            Network.getListCarrier(
                onSuccess = {
                    for (item in it){
                        addLog(item.toString())
                    }
                },
                onError = {
                    addLog(it.toString())
                },
                doOnSubscribe = {
                    Log.d("api", "start")
                },
                doOnTerminate = {
                    Log.d("api", "end")
                }
            )
        }

        binding.suspendGetBtn.setOnClickListener {
            Network.getCarrierTracks(
                onSuccess = {
                    addLog(it.toString())
                },
                onError = {
                    addLog(it.message.toString())
                    //addLog("${(it as HttpException).code()}")
                }
            )
        }

        binding.loginBtn.setOnClickListener {
            val token = "google id_token"
            val reqLogin = ReqLogin.createReqLoginInfo("GOOGLE", token)
            Network.login(reqLogin,
                onSuccess = {
                    addLog(it.toString())
                }, onError = {
                    addLog(it.message.toString())
                }
            )
        }

        binding.clearLogBtn.setOnClickListener{
            binding.log.text = ""

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addLog(logText: String) {
        //Log.d(TAG, logText)

        binding.log.text = logText.plus("\n"+binding.log.text)

    }


    companion object {
        private const val TAG = "RetrofitApisFragment"
    }
}