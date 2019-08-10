# Run Redis in persistent mode
docker run -it --rm --name redis -p 6379:6379 redis redis-server --appendonly yes