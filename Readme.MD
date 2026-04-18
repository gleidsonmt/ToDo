<h1 align="center">JavaFx TO DO Clone</h1>

[//]: # "[![News](https://img.shields.io/badge/News-1-brightgreen.svg?style=for-the-badge)](http://gleidson28.blogspot.com)"
[//]: # "![Version](https://img.shields.io/badge/Version-0.3.22-green.svg?style=for-the-badge)"
[//]: # "[![Release](https://img.shields.io/badge/Release-v2.2.25-green.svg?style=for-the-badge)](https://github.com/gleidsonmt/GNDecorator/releases/tag/2.1.25)"
[//]: # "![Build](https://img.shields.io/badge/Build-2.2.24+258-gold.svg?style=for-the-badge)"

[![License](https://img.shields.io/github/license/Gleidson28/GNDecorator.svg?style=for-the-badge)](https://github.com/gleidsonmt/GNDecorator/blob/master/LICENSE)

<!-- <p align="center">
  <img src="./src/main/resources/io/github/gleidsonmt/todo/img/example.png"  />
</p> -->

# 📑 Overview

This is a part of my personal studies, it focus on design system learning, using a classical to do application. After looking at these examples how to do online I decide to made my own version with javafx inspired by them. <br>

One of my favorite tools is a Microsoft ToDo app, that's the reason they look a like, I don't have any intention to create a competive tool, it's focus on learning. <br>

To be more specific I really focus on learn MVVM, listeners, generics , inheritance, reusable code, at some point I just notice that my code is more behavior than create things, I literaly get lost using much listeners, suffer to understand concurrency, but that's the journey.

# 💻 Environment

#### The project is build using common programming tools like Java, Gradle, JavaFx, MySQL<br>

---

The version of the tools can make trouble, then that's the versions I've been using<br>
Java 23.0.2 (it's the java on my computer) <br>
Gradle 8.14 (wrapper version) <br>
MySQL 8.14.4 <br>
All dependecies are managed by gradle just make sure you're using complatible versions.

## 🪚 Tools <br>

[HeidiSql](https://www.heidisql.com/download.php?download=installer), to manage my database. <br>
ScenicView, to see an interact with the project when it's running.<br>
CSSFX, to update css in real time. <br>
Glad, That's one I like to make custom controls, and other things I create for DashboardFx project. I decide to use as a project apart.

# 🎨 Design Resources

### Icons

#### [Google Icons](https://fonts.google.com/icons)

### Images

#### [Empyt List Image ](https://dribbble.com/shots/3104572-Checklist-Graphic)

# 🚀 Building

I really recommend to use jpackage to create your jar and the .exe. <br>
!🗒️Note ** The generate you .exe you need the wix tool, you can get the url if you using intellij or from here https://wixtoolset.org 🥸\*** <br>
In intellij or visual studio (in visual you need to prepare the ide to use javafx) it's really simple, only open the tab gradle and run the command build after jpackage.
With visual sutdio, use command line to build.<br> At start I go with <code>./gradlew clean build</code> to build the project, and <code>./gradlew run</code> (to run right!?) and finnaly to generate the .exe <code>./gradlew jpackage</code><br>
!🗒️Note \*\* Remove the dependencies from scenicview and cssfx, before use jpackage command, this dependencies are used to test and will impact the final release.

# 📫 Contributing to Project

<!--- Se o seu README for longo ou se você tiver algum processo ou etapas específicas que deseja que os contribuidores sigam, considere a criação de um arquivo CONTRIBUTING.md separado--->

To contribute to the project, follow these steps:

1. Fork this repository.
2. Creates a branch: `git checkout -b <branch_name>`.
3. Do your changes and commit: `git commit -m '<commit_message>'`
4. Send a branch to origin: `git push origin develop / <local>`
5. Creates pull request.

However, if you don't know [How to create a pull Request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

# 😄 Be a contributor <br>

🤖I really need more friends… Send me an <a href='mailto:gleidisonmt@gmail.com?subject=Hi, I see you need my help!.. I am here.'> email <a/>.

# 📝 License

This project is under license. See the file [LICENSE](LICENSE) to more details.

[⬆ Back to the top](#DashboardFx)<br>
