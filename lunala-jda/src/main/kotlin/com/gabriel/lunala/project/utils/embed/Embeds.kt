package com.gabriel.lunala.project.utils.embed

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import java.awt.Color
import java.time.LocalDateTime
import java.time.temporal.TemporalAccessor

@Suppress("unused")
class EmbedBuilderDSL {

    private var headerCallback: Header.() -> Unit = {}
    private var fieldSetCallback: FieldSet.() -> Unit = {}
    private var imagesCallback: Images.() -> Unit = {}
    private var footerCallback: Footer.() -> Unit = {}

    fun header(callback: Header.() -> Unit) {
        this.headerCallback = callback
    }

    fun fieldset(callback: FieldSet.() -> Unit) {
        this.fieldSetCallback = callback
    }

    fun images(callback: Images.() -> Unit) {
        this.imagesCallback = callback
    }

    fun footer(callback: Footer.() -> Unit) {
        this.footerCallback = callback
    }

    fun build(): MessageEmbed {
        val header = Header().apply(headerCallback)
        val fields = FieldSet().apply(fieldSetCallback)
        val images = Images().apply(imagesCallback)
        val footer = Footer().apply(footerCallback)

        return EmbedBuilder().also {
            it.setTitle(header.title)
            it.setDescription(header.description)
            it.setAuthor(header.author?.name, header.author?.url, header.author?.icon)

            it.addFields(fields.fields)

            it.setThumbnail(images.thumbnail)
            it.setImage(images.image)
            it.setColor(images.color)

            it.setFooter(footer.text, footer.icon)
            it.setTimestamp(footer.timestamp)
        }.build()
    }

    class Header {

        var author: Author? = null
        var title: String? = null
        var description: String? = null

        fun author(author: Author) {
            this.author = author
        }

        fun author(name: String, url: String, icon: String) {
            author(Author(name, url, icon))
        }

        fun author(user: User) {
            author("${user.asTag} (${user.idLong})", user.defaultAvatarUrl, user.effectiveAvatarUrl)
        }

        class Author(val name: String, val url: String, val icon: String)

    }

    class FieldSet  {

        val fields: MutableSet<MessageEmbed.Field> = mutableSetOf()

        fun field(callback: Field.() -> Unit) {
            fields.add(Field().apply(callback).toJDA())
        }

        class Field {

            var name: Any = "Kurama foof?"
            var value: Any = "Sisi kurama mt foof uwu"
            var inline: Boolean = false

            fun toJDA(): MessageEmbed.Field =
                    MessageEmbed.Field(name.toString(), value.toString(), inline)

        }

    }

    class Images {

        var thumbnail: String? = null
        var image: String? = null
        var color: Color? = null

    }

    class Footer {

        var text: String? = null
        var icon: String? = null
        var timestamp: TemporalAccessor? = null

        fun now(): TemporalAccessor =
                LocalDateTime.now()

    }

}

fun embed(block: EmbedBuilderDSL.() -> Unit): MessageEmbed =
        EmbedBuilderDSL().apply(block).build()

fun EmbedBuilder.addFields(fields: Collection<MessageEmbed.Field>): Unit =
        fields.forEach { addField(it) }