
package com.path_finder_demo.game;

import java.util.Set;

/**
 * WIP
 * Control system for emitted noises
 * @author masaques
 *
 */

public class NoiseHandler {
	private Set<NPC> npc_set ;
	
	public NoiseHandler(Set<NPC> npc_set){
		this.npc_set = npc_set ;
	}
	
	/**
	 * Tells every NPC in range to act accordingly 
	 * TODO : distinction between NPCs
	 * @param noise
	 */
	public void warn(Noise noise){
		for (NPC npc : npc_set){
			if (npc.getPosition().dst(noise.getSource())<= noise.getRange()){
				if (!noise.getEmitter().getClass().isInstance(npc)) {
					npc.moveTo(noise.getSource());
				}
			}
		}
	}
}
