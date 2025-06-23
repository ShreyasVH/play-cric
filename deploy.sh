if ! lsof -i :$PORT > /dev/null; then
    echo "Starting"
    ./dist/bin/$REPO_NAME -jvm-debug $DEBUG_PORT -Dhttp.port=$PORT > server.log 2>&1 &
fi