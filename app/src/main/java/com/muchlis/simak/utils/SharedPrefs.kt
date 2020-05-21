package com.muchlis.simak.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val _prefsFileName = "prefs"

    //URL
    private val _baseUrl = "baseUrl"

    //TOKEN
    private val _authToken = "authTokenSave"

    //DATA USER DIMUAT KETIKA LOGIN
    private val _name = "nameAccount"
    private val _company = "companyName"
    private val _userBranchOne = "userBranch_one"
    private val _isAdmin = "isAdmin"
    private val _isTally = "isTally"
    private val _isForeman = "isForeman"
    private val _isAgent = "isAgent"

    private val prefs: SharedPreferences = context.getSharedPreferences(_prefsFileName, 0)

    var baseUrl: String
        get() = prefs.getString(_baseUrl, INTERNET_SERVER) ?: INTERNET_SERVER
        set(value) = prefs.edit().putString(_baseUrl, value).apply()

    var authTokenSave: String
        get() = prefs.getString(_authToken, "") ?: ""
        set(value) = prefs.edit().putString(_authToken, value).apply()

    var nameSave: String
        get() = prefs.getString(_name, "") ?: ""
        set(value) = prefs.edit().putString(_name, value).apply()

    var companySave: String
        get() = prefs.getString(_company, "") ?: ""
        set(value) = prefs.edit().putString(_company, value).apply()

    var userBranchSave: String
        get() = prefs.getString(_userBranchOne, "") ?: ""
        set(value) = prefs.edit().putString(_userBranchOne, value).apply()

    var isAdmin: Boolean
        get() = prefs.getBoolean(_isAdmin, false)
        set(value) = prefs.edit().putBoolean(_isAdmin, value).apply()

    var isTally: Boolean
        get() = prefs.getBoolean(_isTally, false)
        set(value) = prefs.edit().putBoolean(_isTally, value).apply()

    var isManager: Boolean
        get() = prefs.getBoolean(_isForeman, false)
        set(value) = prefs.edit().putBoolean(_isForeman, value).apply()

    var isAgent: Boolean
        get() = prefs.getBoolean(_isAgent, false)
        set(value) = prefs.edit().putBoolean(_isAgent, value).apply()
}