package com.gabriel.lunala.project

import com.gabriel.lunala.project.commands.TestBattleCommand
import com.gabriel.lunala.project.module.StandardModule

class BattlesModule(name: String, lunala: Lunala) : StandardModule(name, lunala) {

    override fun onStart() {
        registerCommand(TestBattleCommand().create())
    }

}