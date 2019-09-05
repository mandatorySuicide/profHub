var fs = require('fs');
function findFileName(){
    let [runner,jsFile,...files]=process.argv;
    return files;
}
function inconvinientEmail(name,email){
    const pos = email.indexOf("@");
    if(pos===-1)return true;
    const namePartOfEmail = email.substr(0, pos).toLowerCase();
    // console.log("name part",namePartOfEmail);
    return name.trim().toLowerCase().indexOf(namePartOfEmail)===-1;
}
const files= findFileName();
// ./1.0/upm.json
files.forEach(fileName=>{
    const fileToProcess = `./1.0/${fileName}`;
    fs.readFile(fileToProcess, 'utf8', function(err, contents) {
        let seen=[];
        let json = JSON.parse(contents);
        json.profs.forEach(prof => {
            //console.log("prof",prof);
            let {name,email}=prof;
            let key = name.trim().toLowerCase();
            if(seen[key]){
                console.log("\x1b[41m",`Professor already listed ${JSON.stringify({name,email})} in file = ${fileName}`);
                // throw new Error(`Professor already listed`);
            }
            else if(inconvinientEmail(name,email)){
                //console.log(, stringToMakeYellow);  //yellow
                console.log('\x1b[33m%s\x1b[0m',`Inconvinient professor name,email combination ${JSON.stringify({name,email})} in file = ${fileName}`);
            }
            seen[key]=true;
        });
        console.log(`Processing complete for file ${fileToProcess}`);
    });
});