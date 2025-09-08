Following is API and response.

1)

GET:http://localhost:9097/logs
[
    {
        "id": 1,
        "timestamp": "2025-09-04T19:29:27.878941",
        "message": "Cache refreshed successfully",
        "level": "ERROR"
    },
    {
        "id": 2,
        "timestamp": "2025-09-04T19:29:57.653724",
        "message": "User login successful",
        "level": "WARN"
    },
    {
        "id": 3,
        "timestamp": "2025-09-04T19:30:27.663144",
        "message": "External API call failed",
        "level": "WARN"
    },
]
2) errortype anf count
GET:http://localhost:9097/logs/count
{
    "ERROR": 23,
    "INFO": 17,
    "DEBUG": 22,
    "WARN": 23
}

3) line graph
GET: http://localhost:9097/logs/linegraph
[
    {
        "timestamp": "2025-09-04T19:25",
        "ERROR": 1,
        "WARN": 1,
        "INFO": 0,
        "DEBUG": 0
    },
    {
        "timestamp": "2025-09-04T19:30",
        "ERROR": 3,
        "WARN": 4,
        "INFO": 0,
        "DEBUG": 3
    },
    {
        "timestamp": "2025-09-04T19:35",
        "ERROR": 1,
        "WARN": 2,
        "INFO": 2,
        "DEBUG": 5
    },
    {
        "timestamp": "2025-09-04T19:40",
        "ERROR": 4,
        "WARN": 4,
        "INFO": 2,
        "DEBUG": 0
    },
    
]


