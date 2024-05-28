# profHub
To send email to professors automatically(&amp;safely,&amp;transparently)

.\gradlew  build
- java -jar build\libs\profhunter-1.0.jar -e=your_email@gmail.com -p=your_password -u=https://pastebin.com/raw/wqV7a26w -c=C:\\work\\profHunter\\src\\main\\resources


# How to build and run ():(in windows)
- delete everything under build/libs folder
- Press window + r
- type cmd
- press enter
- then cd to the root directory of the project
- gradlew build
- java -jar .\build\libs\profhunter-1.0.jar


# How to run :(in windows)
- make sure the jar file "profhunter-1.0.jar" exists under build/libs
- Press window + r
- type cmd
- press enter
- then cd to the root directory of the project
- java -jar .\build\libs\profhunter-1.0.jar

# Version
1.0

# Update
This app is intended to run using gmail. To enable automatic email from apps as of 2023,March this is the approach
- Google Account Settings->Security->Enable2fa->Generate apppassword
- Use the generated apppassword.

# Update 2:
Google keeps changing way to generate APP password. This time I tried this - https://myaccount.google.com/apppasswords 
But at first we need enable MFA
