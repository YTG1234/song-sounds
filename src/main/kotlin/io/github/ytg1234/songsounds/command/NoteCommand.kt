package io.github.ytg1234.songsounds.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.ytg1234.songsounds.util.currentSong
import io.github.ytg1234.songsounds.util.index
import io.github.ytg1234.songsounds.util.section
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.TranslatableText

object NoteCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("note").then(
                CommandManager.argument("section", IntegerArgumentType.integer(0))
                    .then(CommandManager.argument("note", IntegerArgumentType.integer(0)).executes(::execute))
            )
        )
    }

    private fun execute(ctx: CommandContext<ServerCommandSource>): Int {
        if (currentSong == null) {
            ctx.source.sendError(TranslatableText("error.songsounds.nosong"))
            return 0
        }

        section = IntegerArgumentType.getInteger(ctx, "section")
        index = IntegerArgumentType.getInteger(ctx, "note")
        ctx.source.sendFeedback(
            TranslatableText(
                "text.songsounds.switchednote",
                index,
                section,
                currentSong!!.sections[section].name,
                currentSong!!.name
            ),
            true
        )

        return Command.SINGLE_SUCCESS
    }
}
