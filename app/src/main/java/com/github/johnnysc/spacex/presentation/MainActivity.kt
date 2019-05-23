package com.github.johnnysc.spacex.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import org.koin.android.ext.android.get
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.getViewModel
import com.github.johnnysc.spacex.observe
import com.github.johnnysc.spacex.presentation.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainScreenViewModel by lazy {
        getViewModel<MainScreenViewModel>(
            MainScreenViewModel.Factory(get(), get())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        viewModel.screenIdLiveData.observe(this) { id ->
            navController.navigate(id)
        }
        viewModel.progressStateLiveData.observe(this) { show ->
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
        viewModel.errorStateLiveData.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = fetch(query)

            override fun onQueryTextChange(newText: String) = fetch(newText)

            private fun fetch(text: String): Boolean {
                viewModel.fetch(text)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}