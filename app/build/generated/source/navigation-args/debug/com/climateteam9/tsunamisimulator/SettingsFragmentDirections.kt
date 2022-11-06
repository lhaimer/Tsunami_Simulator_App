package com.climateteam9.tsunamisimulator

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class SettingsFragmentDirections private constructor() {
  public companion object {
    public fun actionSettingsToAccSettings(): NavDirections =
        ActionOnlyNavDirections(R.id.action_settings_to_accSettings)
  }
}
