package com.uigitdev.mailapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.uigitdev.mailapp.R
import com.uigitdev.mailapp.database.InternalDatabase
import com.uigitdev.mailapp.design.BottomComponent
import com.uigitdev.mailapp.design.fabmenu.FabMenu
import com.uigitdev.mailapp.design.fabmenu.adapter.FabMenuItemAdapter
import com.uigitdev.mailapp.fragment.EmailFragment
import com.uigitdev.mailapp.fragment.SavedFragment
import com.uigitdev.mailapp.fragment.SentFragment
import com.uigitdev.mailapp.list.adapter.SavedAdapter
import com.uigitdev.mailapp.list.adapter.SentAdapter
import com.uigitdev.mailapp.list.data.ListData
import com.uigitdev.mailapp.tools.MTools
import java.util.*

class MainActivity : AppCompatActivity(), BottomComponent.BottomComponentListener, FabMenuItemAdapter.FabMenuListener, SavedAdapter.SavedAdapterListener, SentAdapter.SentAdapterListener {
    private lateinit var toolbar: Toolbar
    private var backClick = 0
    private lateinit var bottomComponent: BottomComponent
    private lateinit var fab: FloatingActionButton
    private lateinit var fabMenu: FabMenu
    private lateinit var fabView: View
    private var fabIsOpen = false
    private lateinit var overshootInterpolator: OvershootInterpolator

    override fun onBackPressed() {
        backClick++
        if (backClick == 1) {
            Toast.makeText(this@MainActivity, getString(R.string.exit_msg), Toast.LENGTH_LONG)
                .show()
            val handler = Handler()
            handler.postDelayed({ backClick = 0 }, 2000)
        } else if (backClick == 2) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setType()
        setToolbar()
        setFabSettings()
        setBottomComponentStyle()
    }

    private fun setType() {
        toolbar = findViewById(R.id.toolbar)
        fab = findViewById(R.id.fab)
        InternalDatabase(this@MainActivity)
        bottomComponent = BottomComponent(findViewById(R.id.bottom_component), this@MainActivity)
        fabView = findViewById(R.id.fab_menu)
        fabMenu = FabMenu(fabView, this@MainActivity, this@MainActivity)
        overshootInterpolator = OvershootInterpolator()
    }

    private fun setFabSettings() {
        showFabWithAnimation()
        fab.setOnClickListener {
            if (fabIsOpen) {
                fabIsOpen = false
                fabView.visibility = View.INVISIBLE
            } else {
                fabIsOpen = true
                fabView.visibility = View.VISIBLE
            }
            fabAnimation(fabIsOpen)
        }
        fabMenu.getCloseButton().setOnClickListener(View.OnClickListener {
            fabIsOpen = false
            fabView.visibility = View.INVISIBLE
            fabAnimation(fabIsOpen)
        })

        fabMenu.setList(ListData().getFabMenuList(this@MainActivity)!!)
    }

    private fun fabAnimation(isOpen: Boolean) {
        if (isOpen) {
            fab.animate().setInterpolator(overshootInterpolator).rotation(45f).setDuration(250)
                .start()
        } else {
            fab.animate().setInterpolator(overshootInterpolator).rotation(0f).setDuration(250)
                .start()
        }
    }

    private fun showFabWithAnimation() {
        fab.visibility = View.INVISIBLE
        fab.scaleX = 0.0f
        fab.scaleY = 0.0f
        fab.alpha = 0.0f
        fab.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                fab.viewTreeObserver.removeOnPreDrawListener(this)
                fab.postDelayed({ fab.show() }, 100)
                return true
            }
        })
    }

    @SuppressLint("ResourceType")
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.parseColor(getString(R.color.colorTitle)))
    }

    @SuppressLint("ResourceType")
    private fun setBottomComponentStyle() {
        bottomComponent.setItemBackgroundColor(getString(R.color.colorCardBackground))
        bottomComponent.setSelectedColor(getString(R.color.colorIconTint))
        bottomComponent.setUnselectedColor(getString(R.color.colorIconRound))
        bottomComponent.setIcons(
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_contacts_24,
            R.drawable.ic_baseline_library_books_24
        )
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(fragment.toString())
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
    }

    override fun selectedTab(BOTTOM_ITEM: Int) {
        when (BOTTOM_ITEM) {
            BottomComponent.HOME -> {
                title = getString(R.string.item_home)
                loadFragment(SentFragment(this@MainActivity))
            }
            BottomComponent.EMAIL -> {
                title = getString(R.string.item_email)
                loadFragment(EmailFragment())
            }
            BottomComponent.SAVED -> {
                title = getString(R.string.item_saved)
                loadFragment(SavedFragment(this@MainActivity))
            }
        }
    }

    override fun optionClick(clickId: Int) {
        when (clickId) {
            0 -> {
                //Create Letter
                startActivity(Intent(this@MainActivity, CreateLetterActivity::class.java))
            }
            1 -> {
                //Create Template
                startActivity(Intent(this@MainActivity, CreateTemplateActivity::class.java))
            }
            2 -> {
                //Add Email Address
                startActivity(Intent(this@MainActivity, AddEmailActivity::class.java))
            }
            3 -> {
                //Import Email Addresses
                startActivity(Intent(this@MainActivity, ImportEmailActivity::class.java))
            }
            4 -> {
                //Delete Lists
                //Delete Lists
                startActivity(Intent(this@MainActivity, DeleteListsActivity::class.java))
            }
        }
        fabIsOpen = false
        fabView.visibility = View.INVISIBLE
        fabAnimation(fabIsOpen)
    }

    override fun menuItemClick(msg: String?) {
        MTools.customTextSnack(
            findViewById(android.R.id.content),
            Objects.requireNonNull(this@MainActivity),
            msg
        )
    }

    override fun dataRefresh(msg: String?) {
        MTools.customTextSnack(
            findViewById(android.R.id.content),
            Objects.requireNonNull(this@MainActivity),
            msg
        )
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.item_account -> startActivity(
                Intent(
                    this@MainActivity,
                    AccountActivity::class.java
                )
            )
        }
        return super.onOptionsItemSelected(item)
    }
}