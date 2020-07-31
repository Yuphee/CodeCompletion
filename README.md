## CodeCompletion
Android Plugin Fast Code Completion

## preview
目前就提供五种代码后缀快速补全 <br>
### .pit 
Init presenter <br> presenter = new xxPresenterImpl(); <br> presenter.attachView(this); <br>
### .timer 
Create rx timer <br> Observable.timer(10, TimeUnit.SECONDS)...; <br>
### .interval 
Create rx interval <br>  Observable.interval(0, 1, TimeUnit.SECONDS)...; <br>
### .isempty
Create if is empty str <br> if (TextUtils.isEmpty(str)) { }; <br>
### .isnotempty 
Create if is not empty str <br> if (!TextUtils.isEmpty(str)) { }; <br>
功能简介：<br><br>
![image](/code_completion.png)
