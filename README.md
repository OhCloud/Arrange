# ARRANGE
    
    ARRANGE is a closet organization app to help me and you figure out whats in our closet and how to maximize what we have and finally solving the age old problem of feeling like we have nothing to wear.
    
    ## Installation
    
    ARRANGE was created using Android Studio for front and back end, firebase for databases, and in java. 
    
    ## Connecting App to GitHub
    
    Connecting my app to github was easy based on this article: https://medium.com/code-yoga/how-to-link-android-studio-with-github-312037a13b99 
    
    Files did not turn 'white' as in Step 4, had to trouble shoot and found this command: git config --global core.autocrlf false and it worked. Just cd into the right file, git add, git commit, and git push and all files will turn white in android studio.
    
    ## Trouble Shooting Installation
    
    First app built in AS crashed and after hrs of trouble shooting, advice was to start over. Upon 2nd try, it worked flawlessly!
    
    ## Trouble Shooting
    
    Originally I thought the workflow for this app would be as follows:
    
    1. open app to home screen which contains:
    	a. upload button
    	b. naming text file area
    	c. camera button (to open camera) 
    	d. gallery button (to see previously uploaded outfits)
    	e. closet button (to make the outfits)
    
    Upon trying and trying and trying, camerax could not be implemented with firebase. I created a work around in which the user has to upload photos from their own device. This worked out well and while I wouldve liked to use the camera feature, I prefer for my app to be stable and reliable rather than flashy. 
    
    
    ## Additional Files Required
    
    
    
    
    ## Firebase HELP
    
    Simple Set Up Steps: https://www.youtube.com/watch?v=6u0gzjth4IE 
    
    3. Upload and Retreive Images [PICK IMAGE FROM DEVICE]:https://www.youtube.com/watch?v=gqIWrNitbbk 
    
    4. Upload and Retreive Images [UPLOAD IMAGE]:https://www.youtube.com/watch?v=lPfQN-Sfnjw
    
    5. Upload and Retreive Images [UPLOAD IMAGE]:https://www.youtube.com/watch?v=3LnMk0-k8bw
    
    6. Upload and Retreive Images [ADAPTER CLASS]:https://www.youtube.com/watch?v=0B471wsJ_RY
    
    7. Upload and Retreive Images [RETREIEVE IMAGES]:https://www.youtube.com/watch?v=p8UNBK06Bf4
    
    8. *Upload and Retreive Images [FLOATING CONTEXT MENU AND INTERFACE]:https://www.youtube.com/watch?v=AYgyF3V8_hE
    
    9. *Upload and Retreive Images [DELETE IMAGES]:https://www.youtube.com/watch?v=n2vcTdUpsLI
    
    10. 
    
    ## WireFrames
    
    ~~[JustInMind](https://www.justinmind.com/)
    https://docs.google.com/document/d/1JM_eno2I75glT_mO-Il2XkifJULh_RLVd_ol5aSKiPQ/edit~~ 
    
    ## User Instructions
    
    FIRST TIME USER:
    
    1. [From Home Screen]
    
    2. hit 'add item' [this will open the users gallery]
    
    3. you will be brought back to the homepage with your item. 
    
    4. Name your item.
    
    5. hit 'upload'
    
    6. decide if you would like to continue adding to your closet or if you would like to make an outfit.
    
    MAKE AN OUTFIT: 
    
    7. [From Home Screen] hit 'go to closet'
    
    8. scroll thru top row until desired garment 
    
    9. long click, hit select
    
    10. scroll thru bottom row until desired garment 
    
    11. long click, hit select
    
    12. now youve made an outfit, this outfit will show in your gallery.
    
    SEE OUTFITS
    
    13. [From Home Screen] go to Gallery
    
    14. Scroll from top to bottom
    
    15. tada!
