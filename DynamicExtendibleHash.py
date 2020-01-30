'''
mi->global depth.
Dict1->Dictionary of buckets.
DictCnt->Dictionary of local depths of each buckets.
'''
mi = 1
BucketSize = 2
Dict1 = {0: [], 1: []}
DictCnt = {0: 1, 1: 1}

'''
Redistribute:
-> Redistributes the bucket that overflows.
-> First the bucket elements are copied and then the bucket is made empty.
-> Then the local depth of the buckets are incremented.
-> A variable 'flag' is passed which indicates whether the redistribution
	is taking after extending the index or not. If not then we need to assign
	the dangling pointers to these newly distributed buckets.
-> Then the elements are redistributed using insert function.
'''


def Redistribute(Dict1, k, n, flag):
    global mi
    l1 = []
    ld1 = DictCnt[k]
    key = n % 2**ld1
    l1 = Dict1[key].copy()
    print("l1 is: ", l1)
    Dict1[key] = []
    Dict1[key+(2**ld1)] = []
    DictCnt[key] += 1
    DictCnt[key+(2**ld1)] += 1

    if(flag == 1):
        print("I am in IFF!")
        t = 2**mi
        x = key
        whilecondt = ld1+1
        print(DictCnt)
        while(mi-whilecondt):
            Dict1[x+2**(ld1+1)] = Dict1[key]
            Dict1[x+2**(ld1+1)+(2**ld1)] = Dict1[key+(2**ld1)]
            x = x+(2**(ld1+2))
            whilecondt += 1

# ReDistributing the overflow bucket
    for element in range(len(l1)):
        k1 = l1[element] % (2**(ld1+1))
        insertEle(Dict1, l1[element], k1)


'''
FuncExtend:
-> Function to extend the index.
-> First two new empty dictionaries are created.
-> Then they are assigned key values based on the already existing index
	by the formula key+2**mi.
-> Then they are appended to the original dictionary by using update function.
-> Then the global depth (mi) is incremented.
'''


def FuncExtend(Dict1, n):
    global mi
    Dict2 = {}
    DictCnt2 = {}
    # Extending the dictionary
    k = n % (2**mi)
    for key in Dict1.keys():
        x = key+(2**mi)
        DictCnt2[x] = DictCnt[key]
        if(key != k):
            Dict2[x] = Dict1[key]
        else:
            Dict2[x] = []

    mi += 1
    Dict1.update(Dict2)
    DictCnt.update(DictCnt2)
    # print(Dict1)
    # print(DictCnt)


'''
insertEle:
-> Function to insert element.
-> Checks if there is space in the bucket mapped by the key obtained
	by the hash function.
-> If yes, appends the element to the bucket.
-> else checks the local depth of the bucket:
	* If it is equal to global depth then the index needs to be extended,
		then redistribute the bucket with flag value=1 to indicate the
		extension of index.
	* else just redistribute the bucket with flag value=0.
-> Then try and insert the element again by repeating the same process.
'''


def insertEle(Dict1, n, k):
    global mi
    global BucketSize
    l1 = Dict1[k]
    if(len(l1)) < BucketSize:
        l1.append(n)

    else:
        if(DictCnt[k] < mi):
            Redistribute(Dict1, k, n, 1)
            print("Redistribution Successful!!")
            print(Dict1)
            k = n % (2**mi)
            insertEle(Dict1, n, k)
        else:
            FuncExtend(Dict1, n)
            k = n % (2**mi)
            Redistribute(Dict1, k, n, 0)
            insertEle(Dict1, n, k)


'''
-> While loop for continuous insertion of elements.
-> insertEle function called with key, element and dictionary
	as parameters.
'''
Flag = True
while(Flag):
    n = int(input("Enter value"))
    print(Dict1)
    k = n % (2**mi)
    insertEle(Dict1, n, k)
    print(DictCnt)
    print(Dict1)
    s = input("Do you want to add more elements?y/n")
    if s == 'y':
        continue
    else:
        Flag = False
