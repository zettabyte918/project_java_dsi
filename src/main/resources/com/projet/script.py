import subprocess
import re
import time


def load_config(filename):
    """Load MySQL credentials from config file"""
    with open(filename) as f:
        config = f.read()
        username = re.search("username=(.*)", config).group(1)
        password = re.search("password=(.*)", config).group(1)
        dbname = re.search("db.name=(.*)", config).group(1)
    return username, password, dbname


def connect_mysql(username, password, dbname):
    """Try to connect to MySQL and check the exit status"""
    cmd = f'mysql -u {username} -p{password} {dbname} -e "SELECT 1" >/dev/null 2>&1'
    return subprocess.run(cmd, shell=True, stderr=subprocess.DEVNULL).returncode == 0


def execute_query(username, password, dbname, query):
    """Execute MySQL query and return output as list of tuples"""
    cmd = f'mysql -u {username} -p{password} {dbname} -N -e "{query}"'
    output = subprocess.check_output(
        cmd, shell=True, text=True, stderr=subprocess.DEVNULL
    )
    return [tuple(line.split("\t")) for line in output.splitlines()]


def update_reminder_status(username, password, dbname, id):
    """Update the status of the reminder to 1"""
    cmd = f'mysql -u {username} -p{password} {dbname} -e "UPDATE reminders SET status = 1 WHERE id = {id}"'
    subprocess.run(cmd, shell=True, stderr=subprocess.DEVNULL)


def process_reminder(id, title, name, tel):
    """Process a single reminder"""
    print(f"Reminder executed: ID={id}, Title={title}, Name={name}, Tel={tel}")


if __name__ == "__main__":
    # Load MySQL credentials from config.properties
    username, password, dbname = load_config("config.properties")

    if connect_mysql(username, password, dbname):
        print(
            "\033[92m" + f"Successfully connected to '{dbname}' database!" + "\033[0m"
        )

        # Get the current timestamp
        current_time = time.strftime("%Y-%m-%d %H:%M:%S")

        # Iterate through unexecuted reminders and update their status
        query = f"SELECT r.id, r.title, u.name, u.tel FROM reminders r JOIN users u ON r.user_id = u.id WHERE r.scheduled_time <= '{current_time}' AND r.status = 0"
        for row in execute_query(username, password, dbname, query):
            id, title, name, tel = row

            # Update the status of the reminder to 1
            update_reminder_status(username, password, dbname, id)

            # Wait for 2 seconds before processing the next reminder
            time.sleep(2)

            # Process the reminder
            process_reminder(id, title, name, tel)

    else:
        print(f"Failed to connect to {dbname} database")
