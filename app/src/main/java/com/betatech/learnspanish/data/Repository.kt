package com.betatech.learnspanish.data

import com.betatech.learnspanish.data.local.db.DbHelper
import com.betatech.learnspanish.data.local.prefs.PreferencesHelper

interface Repository : DbHelper, PreferencesHelper