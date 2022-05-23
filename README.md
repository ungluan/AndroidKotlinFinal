# AndroidKotlinFinal
======================================

HOW TO HINE ACCESS_TOKEN / API_KEY ....
1. Open gradle.properties and add fake_acess_token.
  Ex: ACCESS_TOKEN = YOUR_ACCESS_TOKEN <<-- Don't fill exactly your's access token, 
  NOTICE: You will change it late. This is fake_token and you will show it in github.
  
2. Add buildConfigField at build.gradle(app)
  Ex: buildConfigField "String", "ACCESS_TOKEN", "\"${ACCESS_TOKEN}\""
  
3. Run command line: git add, git commit , git push

4. And then run git update-index --skip-worktree gradle.properties
  This command line to don't care about the change of gradle.properties --> It will not change in Github 
  
5. At Step 1: fill again your's access_token.
6. Rebuild your's project to Android update property ACCESS_TOKEN in BuildConfig Class
USE: BuildConfig.ACCESS_TOKEN -> This property is genreated as Constant

NOTICE: WHEN IT FINISHED AND YOU WANT SWITCH BRANCH. GIT WILL DISPLAY ERROR:
"error: Your local changes to the following files would be overwritten by checkout:
        gradle.properties"
SOLVE: 
STEP1: git stash push --include-untracked. <--------- IT WILL REMOVE ALL LOCAL CHANGE
STEP2:(OPTION) git stash drop <---------------------- IT WILL DROP THAT STASH 

======================================

HOW TO ADD HEADERINTERCEPTOR

=======================================
QUESTION:
1. recyclerView.setHasFixedSize(true). Why setHasFixedSize = true then my RecyclerView is Empty? What is it's function?
2. binding.executePendingBindings(). Why is it impotant?
=> because it forces the data binding to execute immediately, which allows the RecyclerView to make the correct view size measurements.
            
