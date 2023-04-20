#!/bin/bash

# Load MySQL credentials from config.properties
USERNAME=$(grep -Po '(?<=username=).*' config.properties)
PASSWORD=$(grep -Po '(?<=password=).*' config.properties)
DBNAME=$(grep -Po '(?<=db.name=).*' config.properties)


# Function to echo a string with reminder ID and title
function reminder_func() {
  echo "Reminder executed: ID=$1, Title=$2, Name=$3, Tel=$4"
}

# Try to connect to MySQL and check the exit status
if mysql -u $USERNAME -p$PASSWORD $DBNAME -e "SELECT 1" >/dev/null 2>&1; then
  echo "Successfully connected to MySQL database"

  # Get the current timestamp
  current_time=$(date +"%Y-%m-%d %H:%M:%S")
  echo $current_time
  
  # Iterate through unexecuted reminders and update their status
  mysql -u "$USERNAME" -p"$PASSWORD" -D "$DBNAME" -N -e "SELECT r.id, r.title, u.name, u.tel FROM reminders r JOIN users u ON r.user_id = u.id  WHERE r.scheduled_time <= '$current_time' and r.status = 0" | while read id title name tel; do
    # Update the status of the reminder to 1
    mysql -u "$USERNAME" -p"$PASSWORD" -D "$DBNAME" -e "UPDATE reminders SET status = 1 WHERE id = $id"

    # Call the reminder_fun with id, title, and timestamp as arguments
    # reminder_func "$id" "$title" "$name" "$tel"

    echo "$id"

    # Wait for 2 seconds before processing the next reminder
    sleep 2
  done
else
  echo "Failed to connect to MySQL database"
fi
