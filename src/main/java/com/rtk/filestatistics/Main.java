package com.rtk.filestatistics;

import com.rtk.filestatistics.application.FileStatisticsCommand;
import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.infrastructure.formatter.OutputFormatterContext;
import com.rtk.filestatistics.presentation.ArgumentParser;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            ArgumentParser parser = new ArgumentParser();
            Arguments arguments = parser.parseArguments(args);

            FileStatisticsCommand command = new FileStatisticsCommand();
            Map<String, com.rtk.filestatistics.domain.FileStatistics> statistics = command.execute(arguments);

            OutputFormatterContext formatterContext = new OutputFormatterContext();
            String output = formatterContext.format(statistics, arguments.getOutputFormat());

            System.out.println(output);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
