/* Emoticon message cleanser:
 *
 * Skeleton code written by Farhana Choudhury and Jianzhong Qi, April 2020
 *
 * Algorithms are fun!
 *
 * 
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <assert.h>

#define STAGE_NUM_ONE 1							/* stage numbers */ 
#define STAGE_NUM_TWO 2
#define STAGE_NUM_THREE 3
#define STAGE_NUM_FOUR 4
#define STAGE_HEADER "=========================Stage %d=========================\n"	/* stage header format string */

#define SPLIT_CHR " "
#define SPLIT_STR "%%%%%%%%%%"

#define MAX_LINE_LENGTH 280						/* maximum line length */
#define MAX_NUM_CARDS 100						/* maximum number of cards */
#define MAX_ID_LENGTH 8							/* maximum id length */
#define MAX_TRANS_ID_LENGTH 12					/* maximum transaction id length */
#define MAX_TIME_LENGTH 12						/* maximum time length */
#define YEAR_MONTH_DAY_LENGTH 10				/* maximum time length */


typedef char line_t[MAX_LINE_LENGTH+1];			/* a line */

typedef struct{
    char *id;
    int daily_limit;
    int trans_limit;
    int current_day_amount;
    char *current_day_time;
}card_t;

typedef struct trans_s{
    char *id;
    char *card_id;
    char *time;
    int amount;
    struct trans_s *next;
}trans_t;

/****************************************************************/

/* function prototypes */
void read_one_line(line_t one_line, int max_len);
void print_stage_header(int stage_num);
void stage_one(card_t *one_card);
void stage_two(card_t cards[], int *num_cards);
void stage_three(trans_t **trans);
void stage_four(card_t cards[], int num_cards, trans_t *trans);

/* add your own function prototypes here */


/****************************************************************/

/* main function controls all the action, do NOT modify this function */
int
main(int argc, char *argv[]) {
	/* to hold all input cards */
	card_t cards[MAX_NUM_CARDS];	
    
	/* to hold the number of input cards */
	int num_cards = 0;	
    
    /* to hold root of input transactions */
	trans_t *trans = NULL;	
	
	/* stage 1:  Reading One Credit Card Record  */
	stage_one(&(cards[num_cards])); 
	num_cards++;

	/* stage 2:  Reading All Credit Card Records  */
	stage_two(cards, &num_cards);
	
	/* stage 3:  Reading the Transactions  */ 
	stage_three(&trans);
	
	/* stage 4: Checking for Fraudulent Transactions  */
	stage_four(cards, num_cards, trans);
	
	return 0;
}

/* read a line of input into one_msg */
void
read_one_line(line_t one_line, int max_len) {
	int i = 0, c;
	while (((c = getchar()) != EOF) && (c != '\n') && (c != '\r')) {
		if (i < max_len) {
			one_line[i++] = c;
		} else {
			printf("Invalid input line, toooooooo long.\n");
			exit(EXIT_FAILURE);
		}
	}
	one_line[i] = '\0';
}

/* print stage header given stage number */
void 
print_stage_header(int stage_num) {
	printf(STAGE_HEADER, stage_num);
}

/****************************************************************/

/* append a transaction into linked list*/
void
append_trans_list(trans_t **root, line_t line){
    if ((*root) != NULL){
        append_trans_list(&((*root)->next), line);
    }else{
        (*root) = (trans_t*) malloc(sizeof(trans_t));
        assert((*root));
        
        (*root)->id = (char*) malloc(sizeof(char) * MAX_TRANS_ID_LENGTH + 1);
        assert((*root)->id);
        strcpy((*root)->id, strtok(line, SPLIT_CHR));
        (*root)->id[MAX_TRANS_ID_LENGTH] = '\0';
        
        (*root)->card_id = (char*) malloc(sizeof(char) * MAX_ID_LENGTH + 1);
        assert((*root)->card_id);
        strcpy((*root)->card_id, strtok(NULL, SPLIT_CHR));
        (*root)->card_id[MAX_ID_LENGTH] = '\0';
        
        (*root)->time = (char*) malloc(sizeof(char) * MAX_TIME_LENGTH + 1);
        assert((*root)->time);
        strcpy((*root)->time, strtok(NULL, SPLIT_CHR));
        (*root)->time[MAX_TIME_LENGTH] = '\0';
        
        (*root)->amount = atoi(strtok(NULL, SPLIT_CHR));
        
        (*root)->next = NULL;
        
        printf("%s\n", (*root)->id);
    }
}

/* read information from a line and store into card*/
void
line_to_card(line_t line, card_t *card){
    (*card).id = (char*) malloc(sizeof(char) * MAX_ID_LENGTH);
    assert((*card).id);
    strcpy((*card).id, strtok(line, SPLIT_CHR));
    (*card).daily_limit = atoi(strtok(NULL, SPLIT_CHR));
    (*card).trans_limit = atoi(strtok(NULL, SPLIT_CHR));
    (*card).current_day_amount = 0;
    (*card).current_day_time = 
            (char*) malloc(sizeof(char) * YEAR_MONTH_DAY_LENGTH);
}

/* perform binary search to an array of credit cards*/
card_t *
binary_search(card_t cards[], int l, int r, char *card_id) { 
    if (r >= l) { 
        int mid = l + (r - l) / 2; 
        if (!strcmp(cards[mid].id, card_id)) 
            return &(cards[mid]); 
        if (strcmp(cards[mid].id, card_id) > 0) 
            return binary_search(cards, l, mid - 1, card_id); 
        return binary_search(cards, mid + 1, r, card_id); 
    }
    
    // actually we don't care if card is not existed
    // because we assume it will always be in the array
    return NULL;
} 

/* stage 1: Reading One Credit Card Record  */
void 
stage_one(card_t *one_card) {
	/* print stage header */
	print_stage_header(STAGE_NUM_ONE);
	
	/* read the first card information */
    line_t one_line;
    read_one_line(one_line, MAX_LINE_LENGTH);
    line_to_card(one_line, &(*one_card));
    printf("Card ID: %s\n", (*one_card).id);
    printf("Daily limit: %d\n", (*one_card).daily_limit);
    printf("Transaction limit: %d\n", (*one_card).trans_limit);
    printf("\n");
}

/* stage 2: Reading All Credit Card Records */
void 
stage_two(card_t cards[], int *num_cards) {
	/* add code for stage 2 */
	/* print stage header */
	print_stage_header(STAGE_NUM_TWO);
    int sum_daily_limit = cards[*num_cards - 1].daily_limit;
    
    /* initial largest trans information */
    line_t largest_trans_limit_id;
    int largest_trans_limit = cards[*num_cards - 1].trans_limit;
    strcpy(largest_trans_limit_id, cards[*num_cards - 1].id);
    
    /* read all cards */
	while (*num_cards <= MAX_NUM_CARDS){
        line_t temp_line;
        read_one_line(temp_line, MAX_LINE_LENGTH);
        if (!strcmp(temp_line, SPLIT_STR)){
            break;
        }
        line_to_card(temp_line, &(cards[*num_cards]));
        sum_daily_limit = sum_daily_limit + cards[*num_cards].daily_limit;
        if (largest_trans_limit < cards[*num_cards].trans_limit){
            largest_trans_limit = cards[*num_cards].trans_limit;
            strcpy(largest_trans_limit_id, cards[*num_cards].id);
        }
        (*num_cards)++;
    }

    
    /* print summary */
    printf("Number of credit cards: %d\n", *num_cards);
    printf("Average daily limit: %.2f\n", 
            (double) sum_daily_limit / (*num_cards));
    printf("Card with the largest transaction limit: %s\n", 
            largest_trans_limit_id);
	printf("\n");
}

/* stage 3: Reading the Transactions  */ 
void 
stage_three(trans_t **trans) {
	/* print stage header */
	print_stage_header(STAGE_NUM_THREE);
    while (1){
        line_t temp_line;
        read_one_line(temp_line, MAX_LINE_LENGTH);
        if (temp_line[0] == '\0'){
            // EOF
            break;
        }
        append_trans_list(&(*trans), temp_line);
    }
    
	printf("\n");
}

/* stage 4: check whether a transaction may be fraudulent
 *
 * this stage consumes O(nlogm) (average) time complexity, 
 * given n transactions and m credit card records. It takes 
 * logm time to find a corresponding credit card information
 * using binary search, and this action will be performed
 * n times. 
 */ 
void 
stage_four(card_t cards[], int num_cards, trans_t *trans) {
	/* print stage header */
	print_stage_header(STAGE_NUM_FOUR);
    
    trans_t *current = trans;
    while (current!=NULL){
        card_t *card = binary_search(cards, 0, num_cards, current->card_id);
        int daily_limit = card->daily_limit;
        int trans_limit = card->trans_limit;
        int amount = current->amount;

        char *temp_time = (char*) malloc(sizeof(char) * MAX_TIME_LENGTH);
        assert(temp_time);
        strcpy(temp_time, current->time);
        temp_time[YEAR_MONTH_DAY_LENGTH] = '\0';
        if (strcmp(temp_time, card->current_day_time) != 0){
            strcpy(card->current_day_time, temp_time);
            card->current_day_amount = 0;
        }
        card->current_day_amount = card->current_day_amount + amount;

        printf("%s        ", current->id);
        if (amount > daily_limit && amount > trans_limit){
            printf("OVER_BOTH_LIMITS");
        }else if (amount > daily_limit 
                || daily_limit < card->current_day_amount){
            printf("OVER_DAILY_LIMIT");
        }else if (amount > trans_limit){
            printf("OVER_TRANS_LIMIT");
        }else{
            printf("IN_BOTH_LIMIT");
        }
        current = current->next;
        printf("\n");
    }
    
	printf("\n");
}