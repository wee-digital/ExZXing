package com.coin.samplecoin.ui


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coin.samplecoin.R
import com.coin.samplecoin.onGrantedPermission
import kotlinx.android.synthetic.main.home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewScan.setOnClickListener {
            onGrantedPermission(Manifest.permission.CAMERA) {
                findNavController().navigate(R.id.action_homeFragment_to_scanFragment)
            }
        }
        viewGenarate.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_codeFragment)
        }
    }


}
