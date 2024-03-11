package io.github.auag0.aliuplugins

import android.content.Context
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.entities.Plugin
import com.aliucord.patcher.before
import com.discord.api.user.NsfwAllowance
import com.discord.models.user.MeUser

@AliucordPlugin
class BypassAgeRestriction : Plugin() {
    override fun start(context: Context) {
        patcher.before<MeUser>("getNsfwAllowance") { param ->
            param.result = NsfwAllowance.ALLOWED
        }

        patcher.before<MeUser>("getHasBirthday") { param ->
            param.result = true
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
    }
}
