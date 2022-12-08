# Config.yml

## Log-time-format

this is the format for the time that gets shown in the log files and the default format that is used in the config is `dd-M-yyyy hh:mm:ss`
if you show that as a string it would be for example `19-10-2022 14:22:33` and can be changed in the config if you want to use another format.
You can look at the java documentation for the [SimpleDateFormatter](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) and change the format. Illegal format wil cause the server to throw an error
and the plugin will be disabled.


## Loggers

This section of the config are selectors to disable and enable events that get triggered to get logged to the log file. 
Below this text you are going to find a better explanation about every event that gets triggered and what gets from that event.

### Commands

### Msg

### Signs

### Anvils