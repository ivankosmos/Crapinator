package org.jakz.uncommon.crapinator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Crapinator 
{
	
	protected CommandLine commandLine;
	protected static Options clOptions = new Options();
	static
	{
		clOptions.addOption("help",false,"Print usage help.");
		Option sessionOption = OptionBuilder.withArgName("File size Mb").withDescription("The size of the file to ceate").hasArg().create("s");
		sessionOption.setRequired(false);
		clOptions.addOption(sessionOption);
	}

	public Crapinator() 
	{
		//nothing here
	}

	public static void main(String[] args) throws ParseException, IOException 
	{
		Crapinator m = new Crapinator();
		m.commandLine=constructCommandLine(args);
		m.operate();
		System.out.println("THE END");
	}
	
	protected static CommandLine constructCommandLine(String[] args) throws ParseException
	{
		CommandLine commandLine;
		CommandLineParser parser = new org.apache.commons.cli.GnuParser();
		try
		{
			commandLine = parser.parse(clOptions, args);
		}
		catch (Exception e)
		{
			commandLine = parser.parse(clOptions, new String[]{"-help"});
		}
		return commandLine;
	}
	
	//always to standard output
	protected void printHelp()
	{
		System.out.println("Crapinator - produce a file of set size, filled with random crap");
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar \"Crapinator.jar\"", "", clOptions, "", true);
	}
	
	public byte[] createTemplate(int sizeBytes)
	{
		Random r = new Random(Calendar.getInstance().getTimeInMillis());
		byte[] template = new byte[sizeBytes];
		r.nextBytes(template);
		return template;
	}
	
	public Crapinator operate() throws IOException
	{
		if(commandLine.hasOption("help")||commandLine.hasOption("s")==false)
			printHelp();
		else
		{
			File f = new File("crap.file");
			FileOutputStream fs = new FileOutputStream(f);
			int mbsToWrite = Integer.parseInt(commandLine.getOptionValue("s"));
			byte[] mbTemplate = createTemplate(1000000);
			for(int i=0; i<mbsToWrite; i++)
			{
				fs.write(mbTemplate);
			}
			
			fs.flush();
			fs.close();
		}
		return this;
	}

}
