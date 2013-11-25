/*
 * TrimJar uses ProGuard to shrink fat jar to have smaller footprint
 * 
 *  ProGuard: http://proguard.sourceforge.net/
 */
            
@Grapes([
	@Grab(group='net.sf.proguard', module='proguard-base', version='4.10')
]) 

import proguard.ProGuard

// calling main class of proguard
ProGuard.main(args)