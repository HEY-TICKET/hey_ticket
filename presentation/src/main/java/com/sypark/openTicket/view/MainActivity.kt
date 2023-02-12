package com.sypark.openTicket.view

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sypark.openTicket.R
import com.sypark.openTicket.base.BaseActivity
import com.sypark.openTicket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        val isLogin = true

        if (isLogin) {
            navGraph.setStartDestination(R.id.mainFragment)
        } else {
            navGraph.setStartDestination(R.id.recommendFragment)
        }
        navController.graph = navGraph

        //todo_sypark 수정 해야 될 듯...
        binding.layoutSelector.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_home -> {
                    navController.navigate(RecommendFragmentDirections.actionRecommendFragmentToMainFragment())
                }
                R.id.radio_recommend -> {
                    navController.navigate(MainFragmentDirections.actionMainFragmentToRecommendFragment())
                }
            }
        }

        binding.topTitle.imgSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

}