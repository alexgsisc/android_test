package io.parrotsoftware.qatest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.parrotsoftware.qatest.databinding.MainActivityBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }*/
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}