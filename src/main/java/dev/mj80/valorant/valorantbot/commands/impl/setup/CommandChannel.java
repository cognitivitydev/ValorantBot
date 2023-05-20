package dev.mj80.valorant.valorantbot.commands.impl.setup;

import dev.mj80.valorant.valorantbot.commands.DiscordCommand;
import dev.mj80.valorant.valorantbot.utils.BotUtils;
import dev.mj80.valorant.valorantbot.utils.CoreUtils;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandChannel extends DiscordCommand {

    @Getter
    private final SlashCommandData commandData =
            Commands.slash("commandchannel", "Used to declare the channel where the bot commands should be used.")
                    .addOption(OptionType.CHANNEL, "channel", "The bot command channel", true);

    @Override
    public void run(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        long channel = event.getOption("channel").getAsLong();

        String settingsFile = CoreUtils.readFile("settings.txt");
        List<String> settings = new ArrayList<>(Arrays.asList(settingsFile.split("\n")));

        if (BotUtils.checkRole(event.getMember(), "admin")) {
            if ((BotUtils.checkChannel(event.getChannel(), "modCommand")) || (event.getMember().isOwner())) {
                if (CoreUtils.hasSetting(settings, "botCommandChannel")) {
                    String setting = settings.stream().filter(line -> line.startsWith("botCommandChannel")).findFirst().orElse("");
                    settings.set(settings.indexOf(setting), "botCommandChannel = " + channel);
                } else {
                    settings.add("botCommandChannel = " + channel);
                }
                CoreUtils.writeFileFromList("settings.txt", settings);

                BotUtils.auditSlashCommandAction("commandchannel", event.getChannel(), event.getMember());

                event.getHook().editOriginal("Command was run successfully,").queue();
            } else {
                event.getHook().editOriginal("Sorry, you can't use this command in this channel.").queue();
            }
        } else {
            event.getHook().editOriginal("Sorry, you don't have permission to run this command.").queue();
        }
    }
}
