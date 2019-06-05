package com.github.johnnysc.spacex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.johnnysc.spacex.R
import com.github.johnnysc.spacex.presentation.fragment.RetryListener
import com.github.johnnysc.spacex.presentation.fragment.SearchResultsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RetryListener {

    private lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navController = findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java)
        viewModel.searchState.observe(this, Observer<Pair<Int, String?>> { data ->
            navController.navigate(
                data.first,
                Bundle().apply { putString(SearchResultsFragment.EXTRA_YEAR, data.second) }
            )
        })
        viewModel.progressState.observe(this, Observer<Boolean> { show ->
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
        })
        viewModel.errorState.observe(this, Observer<Int> { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_NUMBER
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

    override fun onOptionsItemSelected(item: MenuItem?) =
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)

    override fun retry() = viewModel.fetch()
}