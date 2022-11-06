package com.climateteam9.tsunamisimulator

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class MapFragmentDirections private constructor() {
  public companion object {
    public fun actionChatListToSettings(): NavDirections =
        ActionOnlyNavDirections(R.id.action_chatList_to_settings)
  }
}
