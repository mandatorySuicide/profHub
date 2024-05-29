docker build -t profhunter .
docker tag profhunter ivplay4689/proffinder
docker push ivplay4689/proffinder

docker run -it --rm \
  -e EMAIL=ahmedmdnajim@gmail.com \
  -e PASSWORD="twgs gism izyl bkjb" \
  -e URL=https://pastebin.com/raw/wqV7a26w \
  -v /Users/najim/Documents/profhunterresumepathtest:/app/config \
  ivplay4689/proffinder