<%
// data-options
var data = "";
if(!isEmpty(options!)){
	data = data + options;
}
if(!isEmpty(placeholder!)){
	data = data + ", placeholder : '" + placeholder + "'";
}
if(!isEmpty(isReadonly!)){
	data = data +  ", isReadonly : " + isReadonly ;
}
if(!isEmpty(multiple!)){
	data = data +  ", multiple : " + multiple ;
}
if(strutil.startWith(data, ",")){
	data = strutil.subString (data,1);
}

// 默认URL
var findUrl = "";
if(!isEmpty(code) && !isEmpty(field)){
	findUrl = "/widget/find?code=" + code +"&field="+ field;
}
if(!isEmpty(url!)){
	// 自定义URL
	findUrl = url;
} else {
	// 自定义表达式
	findUrl = "/widget/find?";
	// 存在自定义表达式
	if(!isEmpty(exp!)){
		findUrl = findUrl + "exp=" + exp; 
	} else {
		if(!isEmpty(code!)){
			findUrl = findUrl + "code="+ code + "&field=" + field;	
		} else {
			findUrl = "";
		}
	}
}
// 将URL作为属性放置于值所在的隐藏文本框上，方面级联时JS修改URL
%>
<div class="eova-find" id="${id!}" name="${name}" value="${value!}" code="${code!}" field="${field!}" url="${findUrl!}" data-options="${data}"></div>