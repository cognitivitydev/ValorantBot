package dev.mj80.valorant.valorantbot.managers;

import dev.mj80.valorant.valorantbot.commands.DiscordCommand;
import dev.mj80.valorant.valorantbot.commands.impl.Link;
import dev.mj80.valorant.valorantbot.commands.impl.Stats;
import dev.mj80.valorant.valorantbot.commands.impl.member.Appeal;
import dev.mj80.valorant.valorantbot.commands.impl.admin.ListSettings;
import dev.mj80.valorant.valorantbot.commands.impl.help.Help;
import dev.mj80.valorant.valorantbot.commands.impl.mod.BanMember;
import dev.mj80.valorant.valorantbot.commands.impl.mod.History;
import dev.mj80.valorant.valorantbot.commands.impl.setup.*;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.ArrayList;

public class CommandManager {
    @Getter private static ArrayList<DiscordCommand> commands = new ArrayList<>() {{
        add(new Link());
        add(new Stats());
        
        //Setup
        add(new AdminRole());
        add(new ModRole());
        add(new AuditChannel());
        add(new CommandChannel());
        add(new ModCommandChannel());
        add(new AppealChannel());

        //Admin
        add(new ListSettings());

        //Mod
        add(new History());
        add(new BanMember());

        //Member
        add(new Appeal());
        
        //Help (KEEP AT BOTTOM)
        add(new Help());
    }};
    @Getter private static ArrayList<SlashCommandData> commandsData = new ArrayList<>();
    
    
    public CommandManager() {
        for(DiscordCommand command : commands) {
            commandsData.add(command.getCommandData());
        }
    }
}
