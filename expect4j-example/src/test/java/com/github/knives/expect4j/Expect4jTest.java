package com.github.knives.expect4j;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.junit.Test;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import expect4j.Closure;
import expect4j.Expect4j;
import expect4j.ExpectState;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;

/**
 * http://nikunjp.wordpress.com/2011/07/30/remote-ssh-using-jsch-with-expect4j/
 * 
 * Remote SSH with expect4j
 */
public class Expect4jTest {
	public static class SSHClient {
		private static final int COMMAND_EXECUTION_SUCCESS_OPCODE = -2;
		private static String ENTER_CHARACTER = "\r";
		private static final int SSH_PORT = 22;
		private List<String> lstCmds = new ArrayList<String>();
		
		// list of prompts to be expected using backslash to escape regex special character
		private static String[] linuxPromptRegEx = new String[] { "\\>", "#", "~#", "\\$" };

		private Expect4j expect = null;
		private StringBuilder buffer = new StringBuilder();
		private String userName;
		private String password;
		private String host;

		public SSHClient(String host, String userName, String password) {
			this.host = host;
			this.userName = userName;
			this.password = password;
		}

		/**
		 *
		 * @param cmdsToExecute
		 */
		public String execute(List<String> cmdsToExecute) {
			this.lstCmds = cmdsToExecute;

			Closure closure = new Closure() {
				public void run(ExpectState expectState) throws Exception {
					buffer.append(expectState.getBuffer());
				}
			};
			List<Match> lstPattern = new ArrayList<Match>();
			for (String regexElement : linuxPromptRegEx) {
				try {
					Match mat = new RegExpMatch(regexElement, closure);
					lstPattern.add(mat);
				} catch (MalformedPatternException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				expect = SSH();
				boolean isSuccess = true;
				for (String strCmd : lstCmds) {
					isSuccess = isSuccess(lstPattern, strCmd);
					if (!isSuccess) {
						isSuccess = isSuccess(lstPattern, strCmd);
					}
				}

				checkResult(expect.expect(lstPattern));
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				closeConnection();
			}
			return buffer.toString();
		}

		private boolean isSuccess(List<Match> objPattern,
				String strCommandPattern) {
			try {
				boolean isFailed = checkResult(expect.expect(objPattern));

				if (!isFailed) {
					expect.send(strCommandPattern);
					expect.send(ENTER_CHARACTER);
					return true;
				}
				return false;
			} catch (MalformedPatternException ex) {
				ex.printStackTrace();
				return false;
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
		}

		private Expect4j SSH() throws Exception {
			JSch jsch = new JSch();
			Session session = jsch.getSession(userName, host, SSH_PORT);
			if (password != null) {
				session.setPassword(password);
			}
			Hashtable<String, String> config = new Hashtable<String, String>();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(60000);
			ChannelShell channel = (ChannelShell) session.openChannel("shell");
			Expect4j expect = new Expect4j(channel.getInputStream(),
					channel.getOutputStream());
			channel.connect();
			return expect;
		}

		private boolean checkResult(int intRetVal) {
			if (intRetVal == COMMAND_EXECUTION_SUCCESS_OPCODE) {
				return true;
			}
			return false;
		}

		private void closeConnection() {
			if (expect != null) {
				expect.close();
			}
		}

	}

	@Test
	public void test() {
		SSHClient ssh = new SSHClient("localhost", "test", "test");
		List<String> cmdsToExecute = new ArrayList<String>();
		cmdsToExecute.add("ls");
		cmdsToExecute.add("pwd");
		cmdsToExecute.add("mkdir testdir");
		String outputLog = ssh.execute(cmdsToExecute);
		System.out.println(outputLog);
	}

}
