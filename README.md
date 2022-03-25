Original App Design Project - README Template
===

# Weather Wear

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Weather Wear is a lifestyle app that recommends an outfit to the user based off the weather forecast for that day. Weather Wear also considers what the user has worn recently, the size of their wardrobe, and what colors go together the best.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Lifestyle
- **Mobile:** Our app will first be developed for mobile but will have room to expand to desktop.
- **Story:** Analyzes users clothing choices and the current weather to recommend users fashion and weather appropriate clothing choices,
- **Market:** Any indivudual that wears can use the app.
- **Habit:** Our users should use this app as part of their daily morning routine.
- **Scope:** First we'll start with recommending outfits. In the future we can recommend clothes from online shops that users do not own yet. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Login
* Create Account
* Homepage where you can view weather and reccomended outfit
* Ability to change reccomended outfit
* Upload new clothes

**Optional Nice-to-have Stories**

* 7-day forecast reccomended
* Automatic color detector if picture is uploaded
* Favorite certain outfits you like
* Favorite outfits will be recommended 
* Block certain outfits so you won't get that combination ever
* Specific weather details (hour by hour)

### 2. Screen Archetypes

* Login
* Register
* Homepage
   * Weather
   * Recommended outfit
* Edit Recommended Outfit
* Wardrobe
   * Add clothes
* Settings
   * Adjust location
* User profile
   * Stat display
      * Daily login counter
      * Does the user dress cold/warm?

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Homepage
* Wardrobe
* User profile
   * Settings

**Flow Navigation** (Screen to Screen)

* Forced Log-in -> Account creation if no log in is available
   * Leads to Homepage
* Homepage
   * Leads to Edit Recommended Outfit
   * Leads to Specific weather detail
* Wardrobe
   * Leads to outfit upload
* Profile
   * Leads to user profile
* Settings
   * Leads to app settings

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
