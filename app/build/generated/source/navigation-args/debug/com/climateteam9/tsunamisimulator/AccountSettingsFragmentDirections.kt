package com.climateteam9.tsunamisimulator

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class AccountSettingsFragmentDirections private constructor() {
  public companion object {
    public fun actionFragmentAccSettingsToFra(): NavDirections =
        ActionOnlyNavDirections(R.id.action_fragmentAccSettings_to_fra)
  }
}
