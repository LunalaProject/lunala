package com.gabriel.lunala.project.util.platform

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SafeMessage(
    val content: String? = null,
    val embed: SafeEmbed? = null
)

@Serializable
data class SafeEmbed @JvmOverloads constructor(
    val title: String? = null,
    val description: String? = null,
    val author: SafeEmbedAuthor? = null,
    val thumbnail: SafeEmbedImage? = null,
    val image: SafeEmbedImage? = null,
    val fields: List<SafeEmbedField> = emptyList(),
    val footer: SafeEmbedFooter? = null
) {

    @Serializable
    data class SafeEmbedAuthor(
        val name: String? = null,
        val url: String? = null,
        @SerialName("icon_url")
        val iconUrl: String? = null
    )

    @Serializable
    data class SafeEmbedImage(
        val url: String? = null,
    )

    @Serializable
    data class SafeEmbedField(
        val name: String? = null,
        val value: String? = null,
        val inline: Boolean = false
    )

    @Serializable
    data class SafeEmbedFooter(
        val text: String? = null,
        @SerialName("icon_url")
        val iconUrl: String? = null
    )

}


fun SafeEmbed.toBuilder() = EmbedBuilder().also {
    it.title = title
    it.description = description

    it.author = EmbedBuilder.Author().also {
        it.name = author?.name
        it.url = author?.url
        it.icon = author?.iconUrl
    }

    it.image = image?.url

    it.thumbnail(thumbnail)

    fields.forEach { field ->
        it.fields.add(EmbedBuilder.Field().also {
            it.name = field.name ?: return@forEach
            it.value = field.value ?: return@forEach
            it.inline = field.inline
        })
    }

    it.footer(footer)
}

private fun EmbedBuilder.thumbnail(thumbnail: SafeEmbed.SafeEmbedImage?) {
    if (thumbnail?.url != null) {
        thumbnail {
            url = thumbnail.url
        }
    }
}

private fun EmbedBuilder.footer(footer: SafeEmbed.SafeEmbedFooter?) {
    if (footer?.text != null) {
        footer {
            text = footer.text
            icon = footer.iconUrl
        }
    }
}