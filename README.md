## Nebula Plugin 1.21.10
An RPG-based Minecraft plugin with a unique structure and system built on services and generalized interactions. The main modules of the system are focused on custom achievements, custom items and crafts, custom mobs and their spawn conditions, and, accordingly, the foundation of all foundations in the form of a skill and experience system.

The plugin is currently built so that it can be supplemented from any point without changing the structure of other elements or services.

* NOTE: The desired result was to implement a configurable system that would allow for the creation of a more customizable and user-friendly system, where skills, achievements, events, and their interactions could be created in the configuration. However, given the time constraints and the current state of experience in game development, the system is simplified but no less limited for the development of individual game elements.


## Tech Info
Base requirements:
- Java 21/23+
- gradle 8.10.2

Project base tech stack:
* paper-api:1.21
* myssql
* HikariCP:5.1
* flyway:10.10

## Core System Features
Achievements
> The system features an achievement system that allows you to track the progress of certain user actions, which can be defined by events subscribed to an internal “bus” that receives events via an official event bridge. *There were also plans to add custom events: skills, achievements, experience, etc.

Skills
> The skill system allows you to store certain data about the user and use it to modify or change the normal behavior of the user's game. Accordingly, skills can appear as simple level values or additional values, in the form of objects or maps, if we take the concept of Java and the current implementation.

Items
> Accordingly, the creation of items and their crafting is designed so that crafting can be dependent on other factors and requires exclusive items rather than vanilla analogues. That is, these items will be unique for creating custom subjects and cannot be replaced by ordinary analogues.

Mobs
> Entities can have modifications to their base values, interact with other aspects of the plugin, and be grouped into “packs.” Mob packs can be used to create spawn conditions that are responsible for ensuring that our mobs can appear in the normal world, with certain chances, conditions, drops, etc.

## License
This project is open source and available under the MIT License.
