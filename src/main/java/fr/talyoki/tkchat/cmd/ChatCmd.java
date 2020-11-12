package fr.talyoki.tkchat.cmd;

import fr.talyoki.tkchat.manager.Manager;
import fr.talyoki.tkchat.manager.ModeratorsGlobalViewManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChatCmd extends Command {
    private ModeratorsGlobalViewManager moderatorsGlobalViewManager;

    public ChatCmd(Manager manager){
        super("chat");
        this.moderatorsGlobalViewManager = manager.moderatorsGlobalViewManager;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        // Si le joueur a la permission de modération
        if(this.hasModerateChatPermissions(sender)) {
            if(args.length >= 2){
                // Commandes pour le debug
                if(args[0].equals("list")){
                    if(args[1].equals("global")){
                        moderatorsGlobalViewManager.listPlayer(sender);
                    }
                    else if(args[1].equals("private")){

                    }else{
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
                    }
                // Commandes pour la modération du chat
                }else if(args[0].equals("modo")) {
                    if(args[1].equals("global")){
                        if(moderatorsGlobalViewManager.isActive((ProxiedPlayer) sender)){
                            // Si le joueur est deja enregistré
                            if(moderatorsGlobalViewManager.removeModo(sender.getName())){
                                // Message de confirmation
                                sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous avez désactivé l'affichage des messages cross serveur"));
                            }
                        }else {
                            // Si le joueur n'est pas enregistré
                            if(moderatorsGlobalViewManager.addModo(sender.getName())){
                                // Message de confirmation
                                sender.sendMessage(new TextComponent(ChatColor.GREEN + "Vous pouvez maintenant voir les messages cross serveur"));
                            }
                        }
                    }
                    else if(args[1].equals("private")){

                    }else{
                        sender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
                    }
                }else {
                    sender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
                }
            }else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Erreur dans la commande"));
            }
        }else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Vous n'avez pas la permission"));
        }
    }

    // Permissions chat modo
    private boolean hasModerateChatPermissions(CommandSender sender) {
        return sender.hasPermission("tkChat.chat.modo");
    }
}
