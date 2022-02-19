#Builder Stage
FROM node:alpine as builder 

WORKDIR '/app'

COPY package.json .
RUN npm install

COPY . .

RUN npm run build 



#Run Stage
FROM nginx:1.15.8

# nginx 의 default.conf 를 삭제
RUN rm /etc/nginx/conf.d/default.conf
# host pc 의 nginx.conf 를 아래 경로에 복사
COPY ./nginx.conf /etc/nginx/conf.d

#Copy production build files from builder phase to nginx
COPY --from=builder /app/build /usr/share/nginx/html
