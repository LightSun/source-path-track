# source-path-track
this is a lib of source path track. eg : if you want yo track A->B->C or A-B->Any page or  Any page ->B ->C

<img src="/imgs/log.png" alt="Demo Screen Capture" width="900px" height="450px"/>

## what is this ?
 often we may want to track the path of user's operate. so this is the library to resolve it.
 such as:  There is some pages . A,B,C and other pages.  and if we want to track
     "A -> B ->C" with "Any -> B -> C" with "A->B->Any".

## design idea or structure

path have multi nodes and some rules. so i declare a class named 'CareAction'. That is a 'CareAction' indicate a path that we want to track. And the class contains some nodes. the class 'TagNode' is it. And the ITrackManager is the manager to manage 
the track behaviour.




 
