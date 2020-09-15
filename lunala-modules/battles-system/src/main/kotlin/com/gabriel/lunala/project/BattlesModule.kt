package com.gabriel.lunala.project

import com.gabriel.lunala.project.commands.TestBattleCommand
import com.gabriel.lunala.project.module.StandardModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class BattlesModule(name: String) : StandardModule(name) {

    override fun onStart() {
        loadKoinModules(module {
            single { this@BattlesModule }
        })

        registerCommand(TestBattleCommand().create())
    }

}