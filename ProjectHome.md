### This project hosts the source code for few Android RSS reader applications. You can see few working examples in the 'Links' section. ###


---


### Each RSS reader provides the following functionality: ###

  * Displays the currently available RSS feeds.
  * Tapping on each individual RSS entry opens browser window and forwards to the news URL.
  * Resizable home/lock screen widget which displays the titles of the news articles.
  * Tapping on the widget displays all currently available RSS feeds.


---


### Each RSS reader can be easily rebranded. You need to do the following: ###

  * Use the source code in your Android project.
  * Change all package declarations.
  * Update the manifest file to reflect the package changes.
  * Provide your own set of icons.
  * Edit the file 'strings.xml' (see below) and provide values of your own.


---


### The customizable keys are the following: ###

**app\_name** - The name of the Android application which is also used as a shortcut name on the desktop as well as for the widget.

**rss\_url** - The URL for the RSS feed.

**default\_text** - The text which is displayed in the title bar while the RSS articles are still loading.

**loaded\_text** - The text which is displayed in the title bar after the RSS articles have been loaded.

**rss\_unavailable\_title** - The text which is displayed in the title field when the RSS feed is unavailable.

**rss\_unavailable\_description** - The text which is displayed in the description field when the RSS feed is unavailable.

**rss\_unavailable\_date** - The text which is displayed in the date field when the RSS feed is unavailable.

**author\_title** - The text which is displayed in the title field for the last RSS entry. The last RSS entry is used as 'About this program' field.

**author\_description** - The text which is displayed in the description field for the last RSS entry. The last RSS entry is used as 'About this program' field.

**author\_date** - The text which is displayed in the date field for the last RSS entry. The last RSS entry is used as 'About this program' field.

**author\_url** - The URL which will be loaded when the user taps on the last RSS entry. The last RSS entry is used as 'About this program' field.

**background** - The background color for the RSS entries.

**stroke** - The color of the border around the RSS entry.

**text** - The font color for all RSS fields (title, description and date).