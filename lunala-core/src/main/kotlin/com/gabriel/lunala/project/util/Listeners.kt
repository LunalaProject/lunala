package com.gabriel.lunala.project.util

import arrow.core.Tuple2
import com.gabriel.lunala.project.SingleLunalaService
import com.gabriel.lunala.project.listener.MentionListener
import com.gabriel.lunala.project.platform.LunalaCluster

fun SingleLunalaService<*>.setupListeners(cluster: LunalaCluster, services: Tuple2<ProfileService, ServerService>) {
    MentionListener(cluster, services).create()
}