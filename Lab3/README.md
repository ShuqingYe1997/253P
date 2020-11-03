# Lab3

Design a program that reads in parenthesized text and, if valid, on a new line prints the text of each nesting level preceded with two spaces for level of nesting.  The text should be printed in order of nesting.  

- Input: a string of parenthesized text from stdin

- Output: if parentheses are valid, then on a new line, prints the text of each nesting level preceded with two spaces for level of nesting to stdout. otherwise, print error message


## Get started  
```Bash
git clone https://github.com/ShuqingYe1997/253P-Lab3.git  
cd 253P-Lab3  
cd src  
javac ParseString.java   
java ParseString  
```
## Example

input:  
(does{[[well!]]}work)this  

output:   
|this   
|>does work   
|>>well!   


input:  
(doesn’t{[[work!]})this  

output:  
|mismatched groups!


