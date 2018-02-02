# Pub-Sub-System

Publish subscribe system is a messaging pattern where subscribers have the ability to express their interest in any event of which they would like to receive the notifications. And publisher have the ability to choose the topic under which he wants to publish the article.

#### The classes used in the building of this system are - 
1. EventManager
2. PubSubAgent
3. Topic
4. Subscriber 
5. Event

EventManager - This class interacts with all the subscriber and publisher and handles all the requests and message passing between pub and sub. The server side coding is written in this class.

PubSubAgent - This class when implemented will act as either a publisher or a subscriber. The client side code is written in this class.

Topic - In this class, we add all the topics under which publisher can publish an article. It consist of an ArrayList which already contains some topics and more can be added, if the publisher wishes.

Subscriber - In this class, a new subscribers port no, an unique ID and the topics list to which the subscriber has subscribed is added. The subscribed topic list is added in an ArrayList. The subscribing and unsubscribing both are done from this arrayList itself.

Event - This class is used to store the article which has been published. It stores the title, topic, content and an unique ID given to each article.

### Implementation

Firstly the event manager is implemented after which pubsubagent is implemented, so the PubSubAgent connects on one port after which a new, random and empty port is generated and this port is sent back to pub subagent which further connects on that port. This way a parent port is always empty and there is no delay in sending the messages since each port is connect to one user.
Whenever the publisher publishes a new article, than a new Event object is created in which topic, title and content of the article is stored. And this object is added in an arrayList called events_list. This list is static in nature.
When a new subscriber comes, than he is asked about the topics he wants to subscribe and than a new subscriber object is created and this information is stored in it. This object is than stored in an ArrayList called subscribers_list which is static in nature.
Working
The program starts with implementation of EventManager and after that PubSubagent. As soon as PubSubAgent is implemented, user will be asked whether the user is a publisher or subscriber. After the user chooses to be one of them, he will be shown other options.
If the user is a publisher than he will be firstly shown some topics under which he can publish or he will be given an option to add his own topic. If the publisher chooses to add new topic than firstly he will be asked the name of the topic and than title and content of the article else he will be just asked for the title and the content of the article. If he has chosen to add new topic and he writes down the name of the topic that is already present in the list than he will be shown that the topic is already present, that is, no new topic will be added in the topics list.

##### If the user chooses to be a subscriber than Firstly he will be shown a menu which will consist of
1.) Subscribe
2.) Unsubscribe
3.) View the articles 
4.) Exit
  
1.)Subscribe - In this, the subscriber will be shown some topics to which he can subscribe. All the topics will be shown in loop until the subscriber types in ‘NO’ to stop from subscribing to the new topics. After which he will be shown all the topics to which he has subscribed to. Subscriber wont be able to add a topic twice. If he tries than he will be shown the message that the topic has already been subscribed.

2.)Unsubscribe - The subscriber will be shown the list of topics to which he has subscribed and now he will be asked to enter the number in front of the topic to which he wants to unsubscribe.That topic will be removed from his subscription list. If he hasn’t subscribed to any topic than he will be shown a message saying ‘He hasn’t subscribed to any topics.’

3.) View the article - If it happens that before the subscriber comes, the publisher has already published few articles under the topics to which subscriber subscribes, than all those articles will be visible under this menu option to which he can view by typing in the number in front of that article.

4.) Exit- when subscribers is done with subscribing and viewing all the articles currently present than he can exit.
While coding, i have made use of Switch- Case for these 4 menu options.
Whenever a new topic or a new article comes up when the subscriber is not online, than this topic or event goes into the message queue which against each and every subscribers stores all the notifications which needs to be shown. Now when publisher is done with publishing than the subscriber is invoked and shown all the topics or the articles. For this purpose, i have used hashtable which stores the port number as key and arraylist as value. All the notifications is tired in array list.
