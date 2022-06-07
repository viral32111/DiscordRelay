package com.viral32111.discordrelay.mixin;

import com.viral32111.discordrelay.CommandExecute;
import com.viral32111.discordrelay.PlayerChat;
import net.minecraft.server.filter.TextStream.Message;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayNetworkHandler.class )
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;

	@Inject( method = "handleMessage", at = @At( "TAIL" ) )
	private void handleMessage( Message message, CallbackInfo info ) {
		String content = message.getRaw();

		if ( content.startsWith( "/" ) ) {
			// TODO: Command Blocks, Sign Commands, Console Commands
			CommandExecute.EVENT.invoker().interact( content.substring( 1 ), player );
		} else {
			PlayerChat.EVENT.invoker().interact( player, message.getFiltered() );
		}
	}
}