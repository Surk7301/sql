#!/bin/bash

# File to store insurance policies
POLICY_FILE="insurance_policies.txt"

# Function to create a new vehicle insurance policy
create_policy() {
    echo "Enter Vehicle Number:"
    read vehicle_no
    echo "Enter Vehicle Type (2-wheeler/4-wheeler):"
    read vehicle_type
    echo "Enter Customer Name:"
    read customer_name
    echo "Enter Engine Number:"
    read engine_no
    echo "Enter Chasis Number:"
    read chasis_no
    echo "Enter Phone Number:"
    read phone_no
    echo "Enter Premium Amount:"
    read premium_amount
    echo "Enter Insurance Type (Full Insurance/ThirdParty):"
    read insurance_type
    echo "Enter Start Date (YYYY-MM-DD):"
    read start_date
    echo "Enter End Date (YYYY-MM-DD):"
    read end_date
    echo "Enter Underwriter ID:"
    read underwriter_id

    # Save the details in the specified format
    echo "$vehicle_no|$vehicle_type|$customer_name|$engine_no|$chasis_no|$phone_no|$premium_amount|$insurance_type|$start_date|$end_date|$underwriter_id" >> "$POLICY_FILE"
    echo "Policy created successfully."
}

# Function to display the policy
display_policy() {
    echo "Enter Vehicle Number to view policy details:"
    read vehicle_no

    if grep -q "^$vehicle_no|" "$POLICY_FILE"; then
        grep "^$vehicle_no|" "$POLICY_FILE" | while IFS="|" read -r vehicle_no vehicle_type customer_name engine_no chasis_no phone_no premium_amount insurance_type start_date end_date underwriter_id; do
            echo "Vehicle Number: $vehicle_no"
            echo "Premium Amount: $premium_amount"
        done
    else
        echo "No policy found for Vehicle Number: $vehicle_no"
    fi
}

# Function to delete the policy
delete_policy() {
    echo "Enter Vehicle Number to delete policy:"
    read vehicle_no

    if grep -q "^$vehicle_no|" "$POLICY_FILE"; then
        grep -v "^$vehicle_no|" "$POLICY_FILE" > temp_file && mv temp_file "$POLICY_FILE"
        echo "Policy deleted successfully."
    else
        echo "No policy found for Vehicle Number: $vehicle_no"
    fi
}

# Main menu
while true; do
    echo "Vehicle Insurance Management"
    echo "1. Create Policy"
    echo "2. Display Policy"
    echo "3. Delete Policy"
    echo "4. Exit"
    echo "Choose an option:"
    read option

    case $option in
        1) create_policy ;;
        2) display_policy ;;
        3) delete_policy ;;
        4) exit 0 ;;
        *) echo "Invalid option. Please try again." ;;
    esac
done

