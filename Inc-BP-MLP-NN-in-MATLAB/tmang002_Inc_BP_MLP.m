% tmang002 - Talha Mangarah
% Uses the following global variables for input and/or output:
%    x   -  input patterns
%    y   -  Node outputs
%    desOut   -  desired output patterns
%    LearnRate -  learning rate parameter
%    I2HWeights  -  first weight layer (updated by this routine)
%    I2OWeights  -  first to Out weight layer (updated by this routine)
%    H2OWeights  -  second weight layer (updated by this routine)

% H2O = Hidden to(2) Out
% I2O = Input to(2) Out
% I2H = Input to(2) Hidden
%--------------------------------------------------------------------------
%%%Variable initialisation%%%
%%Inputs%%
x1 = [0 1];
x2 = [1 0];
%Matrix
x = [x1 ; x2];

%%Hidden outs%%
y1 = 0;
y2 = 0;
y3 = 0;
%Matrix
y = [y1; y2; y3];

%%I2O outs%%
I2Oy1 = 0;
I2Oy2 = 0;
%Matrix
I2Oy = [I2Oy1; I2Oy2];


%%H2O outs%%
H2Oy1 = 0;
H2Oy2 = 0;
H2Oy3 = 0;
%Matrix
H2Oy = [H2Oy1; H2Oy2; H2Oy3];


%%Expected outputs%%
desOut = [1; 1];

%%Weights%%
%%Input to hidden
w1 = 0.1;
w3 = 0.1;
w4 = -0.15;
w6 = 0.35;
%Matrix
I2HWeights = [w1 0; w3 w4; 0 w6];
[I2HRows, I2HCols] = size(I2HWeights);

%%Input to out
w2 = 0.3;
w5 = -0.2;
%Matrix
I2OWeights = [w2, w5];
[I2ORows, I2OCols] = size(I2OWeights);

%%Hidden to out
w9 = -0.2;
w8 = 0.3;
w7 = -0.1;
%Matrix
H2OWeights = [w9, w8, w7];
[H2ORows, H2OCols] = size(H2OWeights);

numInputs = 2;
numPatterns = 2;
numHiddenNodes = 3;
numHiddenLayers = 1;
numOutputs = 1;
numWeights = 9;
LearnRate = 1.0; Momentum = 0; DerivIncr = 0;
TSS_Limit = 0.0001;
deltaI2H = 0; deltaI2O = 0; deltaH2O = 0;
dI2H = [0 0; 0 0; 0 0];
dI2O = zeros(1, numOutputs);
dH2O = zeros(1, numInputs);
Output = zeros(1, 2);
sumOfIn = [0 0];

%--------------------------------------------------------------------------
for  epoch = 1:1
  for curExample = 1:numPatterns
%--------------------------------------------------------------------------
  %%forward prop%%
    %I2H 
    sumOfIn = [0 0];
    for i = 1:I2HRows
        for j = 1:I2HCols 
            sumOfIn(i,j) = I2HWeights(i,j) * x(curExample,j)';
        end
        y(i) = sigmoid(sum(sumOfIn(i,:)));
    end
    
    %I2O
    sumOfIn = [0 0];
    for i = 1:I2ORows
       for j = 1:I2OCols
           sumOfIn(i,j) = I2OWeights(i,j) * x(curExample,j)';
       end
       I2Oy(i) = sum(sumOfIn);
    end
    
    %H2O
    sumOfIn = [0 0];
    for i = 1:H2ORows
       for j = 1:H2OCols
           sumOfIn(i,j) = H2OWeights(i,j) * y(j,i);
           H2Oy(j) = sum(sumOfIn(i,j));
       end
    end
    
    %Output
    Output(curExample) =  sum(H2Oy) + sum(I2Oy);
%--------------------------------------------------------------------------
    %%Back Prop%%
    %Output betas
    for n = numOutputs:-1:1
        %Error
        Error(n) = desOut(curExample) - Output(curExample);
        %Beta deriv%
        %H2O & I2O beta
        OutputBeta(n) = Error(n);
        TSS = sum( Error.^2 );
    
    %Hidden betas
    I2HBeta = [0 0; 0 0; 0 0];
     for i = I2HRows:-1:1
        I2HBetaCalc = 0;
        for j = I2HCols:-1:1
            I2HBetaCalc = I2HWeights(i,j) .* OutputBeta(n);
            I2HBeta(i,j) = (y(i)*(1-y(i))) * I2HBetaCalc;
        end
     end
    end
%--------------------------------------------------------------------------
    %%Weight updates%%
    %H2O Weight update
    for i = 1:numOutputs
        for j = 1:H2OCols
           dH2O(i) = OutputBeta(i) * y(j);
           deltaH2O = LearnRate * dH2O(i) + Momentum * deltaH2O;
           H2OWeights(i,j) = H2OWeights(i,j) + deltaH2O;
        end
    end
    
    %I2O Weight update
    for i = 1:numOutputs
       for j = 1:I2OCols
           dI2O(i) = OutputBeta(i) * x(curExample,j);
           deltaI2O = LearnRate * dI2O(i) + Momentum * deltaI2O;
           I2OWeights(i,j) = I2OWeights(i,j) + deltaI2O;
       end
    end
    
    %I2H Weight update
    for i = numHiddenNodes:-1:1
        for j = I2HCols:-1:1
            dI2H(i,j) = I2HBeta(i,j) * x(curExample,j);
            deltaI2H = LearnRate * dI2H(i,j) + Momentum * deltaI2H;
            I2HWeights(i,j) = I2HWeights(i,j) + deltaI2H; 
        end
    end
    %Set the "false/made up" weights back to 0 in the Input to Hidden layer.
    I2HWeights(1,2) = 0; I2HWeights(3,1) = 0;
  end 
  fprintf('Epoch %3d:  Error = %f\n',epoch,round(TSS,4));
  if TSS < TSS_Limit, break, end
end

%%Sigmoid function%%
function sig = sigmoid(s)
    sig = 1.0 ./ (1.0 + exp(-s));
end